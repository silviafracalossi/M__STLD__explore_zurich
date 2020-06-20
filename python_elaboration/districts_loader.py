import csv
import shapely
from shapely.geometry import Point, Polygon
from shape_creator import string_to_polygon

# Loads districts from 01 - distric.csv file
def load_districts():
    print("[INFO] Loading districts from file...")

    # Defining the district list
    districts = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]

    # Reading the district csv
    with open('../data/original/01 - district.csv') as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        is_header = True

        # Iterating the csv
        for row in csv_reader:

            # Header
            if is_header:
                is_header = False

            # Rows
            else:
                districts[int(row[1])] = string_to_polygon(row[3])

    # Returning list of district id and polygon
    return districts
