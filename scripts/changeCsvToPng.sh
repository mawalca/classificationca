#!/bin/bash

if [ -z "$1" ]; then
	DIR="../results/";
else
	DIR=$1;
fi

find "$DIR" -name "*Image.csv" | while read i; do
	PNG="${i%.*}.png"
	echo "Checking file $PNG";
	if  ! [[ -f "$PNG" ]]; then 
		echo " Creating file .png";
		gnuplot -e "pngname='$PNG';csvname='$i'" plotCsvToPng;
	fi 
done
