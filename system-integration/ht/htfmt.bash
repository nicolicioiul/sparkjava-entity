#! /bin/bash
#
# htfmt.bash replaces horizontal tabs with 8 spaces in files.
#
# Usage:
#     htfmt.bash <file>...
#

for f in "$@"; do
    sed -i -e "s/\t/        /g" "$f"
done
