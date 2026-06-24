#!/usr/bin/perl
#
# Use diatheke to generate files in assets, one per chapter of the Bible

use warnings;
use strict;

if (@ARGV != 2) {
    die("Usage: $0 <version> <directory>\n");
}

my $version = $ARGV[0];
my $command = "diatheke -f HTML -b $version";
my $directory = $ARGV[1];

if ( ! -d $directory ) {
    die("Error: $directory is not a directory\n");
}

my @books;
my @chapters;
open my $BOOK2CHAPTERS, '<', 'book2chapter.tsv';
while (<$BOOK2CHAPTERS>) {
    my @parts = split /\t/;
    push @books, $parts[0];
    push @chapters, $parts[1];
}
close $BOOK2CHAPTERS;

# TESTING
# my @verses = split /\n/, `$command -k Psalms 87`;
# clean_verses(\@verses, "Psalms");
# foreach my $verse (@verses) {
#     print $verse."\n";
# }
# exit 0;

for (my $ii = 0; $ii < @books; $ii++) {
    my $book = $books[$ii];
    my $num_chapters = $chapters[$ii];
    for (my $chapter = 1; $chapter <= $num_chapters; $chapter++) {
        if ( $book eq 'Psalms' && $chapter == 119 ) {
            # Psalm 119 is special in the RRReadings
            my @verses = split /\n/, `$command -k $book 119:1-40`;
            write_verses(\@verses, $book, $directory, "$book 119v1-40");
            @verses = split /\n/, `$command -k $book 119:41-80`;
            write_verses(\@verses, $book, $directory, "$book 119v41-80");
            @verses = split /\n/, `$command -k $book 119:81-128`;
            write_verses(\@verses, $book, $directory, "$book 119v81-128");
            @verses = split /\n/, `$command -k $book 119:129-176`;
            write_verses(\@verses, $book, $directory, "$book 119v129-176");
        } else {
            my @verses = split /\n/, `$command -k $book $chapter`;
            write_verses(\@verses, $book, $directory, "$book $chapter");
        }
    }
}

sub write_verses {
    my ($verses, $book, $directory, $fname) = @_;
    clean_verses($verses, $book);
    open my $OUTPUT, '>', "$directory/$fname";
    foreach my $verse (@$verses) {
        print $OUTPUT "$verse\n";
    }
    close $OUTPUT;
    
}

sub clean_verses {
    my ($verses, $book) = @_;
    # Drop the first "verse", which is just the header
    splice @$verses, 0, 1;
    # Drop the last two "verses", which are (KJV) and </body></html>
    splice @$verses, -2, 2;
    # It puts the psalm title on each verse for some reason
    my $first = 1;
    # Change the foreign whenever we get a new one, not every verse
    my $foreign = "";
    foreach my $verse (@$verses) {
        # Remove tags we don't care about
        $verse =~ s|</head>||;
        $verse =~ s|<body>||;
        $verse = remove_tag($verse, 'span');
        $verse = remove_tag($verse, 'w');
        $verse = remove_tag($verse, 'style');
        $verse = remove_tag($verse, 'milestone');
        $verse = remove_tag($verse, 'chapter');
        # Added to italics
        $verse =~ s|<transChange type="added">([^<]+)</transChange>|<i>$1</i>|g;
        # Divine name to all caps
        $verse =~ s|<divineName>([^<]+)</divineName>|\U$1|g;
        if ( $verse =~ m|<title.*<foreign xml:lang="hbo">([^<]+)</foreign></title>| ) {
            my $new_foreign = $1;
            if ( $foreign eq $new_foreign ) {
                $verse =~ s|<title.*</title> ||g;
            } else {
                $foreign = $new_foreign;
                # I don't like the period
                my $clean_foreign = $foreign;
                $clean_foreign =~ s/\.//;
                $verse =~ s|<title.*</title> |<small>$clean_foreign</small><br />|g;
            }
        }
        # Psalm titles to small
        if ( $first ) {
            $verse =~ s|<title[^>]*>(.+)</title>|<small>$1</small><br />|g;
            $first = 0;
        } else {
            $verse =~ s|<title[^>]*>(.+)</title>||g;
        }
        # Remove the book and chapter, replace with <sup>
        # Except diatheke uses roman numerals...
        $book =~ s/^3/III/;
        $book =~ s/^2/II/;
        $book =~ s/^1/I/;
        # And "Revelation" becomes "Revelation of John"
        $book =~ s/^Revelation$/Revelation of John/;
        $verse =~ s|$book \d+:(\d+): |<sup><b>$1</b></sup> |;
    }
}

sub remove_tag {
    my ($string, $tag) = @_;
    $string =~ s|</$tag>||g;
    $string =~ s/<$tag[^>]*>//g;
    return $string;
}
