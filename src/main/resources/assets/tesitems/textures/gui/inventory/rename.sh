#!/bin/bash
for file in inv_itemlist_border*
do
    cp $file frameBorder${file:19}
    rm $file
done
