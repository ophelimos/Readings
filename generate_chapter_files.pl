#!/usr/bin/perl
#
# Use diatheke to generate files in assets, one per chapter of the Bible

use warnings;
use strict;

if (@ARGV != 2) {
    die("Usage: $0 <version> <directory>\n");
}

my $version = $ARGV[0];
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

for (my $ii = 0; $ii < @books; $ii++) {
    my $book = $books[$ii];
    my $num_chapters = $chapters[$ii];
    for (my $chapter = 1; $chapter <= $num_chapters; $chapter++) {
        my @verses = split /\n/, `diatheke -f CGI -b $version -k $book $chapter`;
        # Drop the last two verses, which are (KJV) and </body></html>
        splice @verses, -2, 2;
        foreach my $verse (@verses) {
            # Remove <span>
            $verse =~ s|</span>||g;
            $verse =~ s/<span[^>]*>//g;
            # Remove the book and chapter, replace with <sup>
            $verse =~ s|^[^:]+\d+:(\d+): |<sup><b>$1</b></sup> |;
        }
        open my $OUTPUT, '>', "$directory/$book $chapter";
        foreach my $verse (@verses) {
            print $OUTPUT "$verse\n";
        }
        close $OUTPUT;
    }
}
