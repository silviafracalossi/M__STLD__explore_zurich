import csv
import json
import shapely
import geopandas as gpd

from shapely.geometry import Point, Polygon
from shape_creator import string_to_point, convert_point

# Elaborates csv and stores elaboration with district id
def elaborate_csv(districts, original_file, id_point_field):
    print("[INFO] Processing csv file "+original_file)
    new_file_lines = []

    # Reading the district csv
    with open('resources/data/01_original/'+original_file) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        header = ""

        # Iterating the csv
        for row in csv_reader:

            # Header
            if header == "":
                header = row
                header[0] = header[0].replace("\"","")
                header.append("fixed_geometry")

            # Rows
            else:

                # Convert location into point
                location_point = ""
                location_point = string_to_point(row[id_point_field])


                # Iterate through districts to find the correct match
                district_id = 0
                for i in range(1, len(districts)):
                    if location_point.within(districts[i]):
                        district_id = i
                        break

                # Insert district id inside the row
                if district_id != 0:
                    row.append(location_point)
                    row.append(district_id)
                    new_file_lines.append([row])

    # Destination file - name preparation
    destination_file = original_file.replace("-", "elaborated")
    destination_file = destination_file.replace(" ", "_")

    # Writing into the destination file
    with open('resources/data/02_python_elaborated/'+destination_file, 'w', newline='') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=',', quoting=csv.QUOTE_MINIMAL)

        # Inserting header
        header.append("district_id")
        spamwriter.writerow(header)

        # Inserting rows
        for line in new_file_lines:
            spamwriter.writerow(line[0])

# Elaborates csv and stores elaboration with district id
def elaborate_bus_csv(districts):

    # Setting method variables
    original_file = "03_04 - bus.csv"
    id_point_field = 14
    id_secondary_point_field = 15

    print("[INFO] Processing csv file "+original_file)
    new_file_lines = []

    # Reading the district csv
    with open('resources/data/01_original/'+original_file) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        header = ""

        # Iterating the csv
        for row in csv_reader:

            # Header
            if header == "":
                header = row
                header[0] = header[0].replace("\"","")
                header.append("fixed_geometry")

            # Rows
            else:

                # Convert location into point
                location_point = ""
                if id_secondary_point_field == 0:
                    location_point = string_to_point(row[id_point_field])
                else:
                    location_point = convert_point(Point(float(row[id_point_field]), float(row[id_secondary_point_field])))


                # Iterate through districts to find the correct match
                district_id = 0
                for i in range(1, len(districts)):
                    if location_point.within(districts[i]):
                        district_id = i
                        break

                # Insert district id inside the row
                if district_id != 0:
                    row.append(location_point)
                    row.append(district_id)
                    new_file_lines.append([row])

    # Destination file - name preparation
    destination_file = original_file.replace("-", "elaborated")
    destination_file = destination_file.replace(" ", "_")
    header.append("district_id")

    # Writing into the destination file
    with open('resources/data/02_python_elaborated/'+destination_file, 'w', newline='') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=',', quoting=csv.QUOTE_MINIMAL)

        # Inserting header
        spamwriter.writerow(header)

        # Inserting rows
        for line in new_file_lines:
            spamwriter.writerow(line[0])

    # Creating the busline file
    bus_line_file = "04_elaborated_only_busline.csv"
    with open('resources/data/02_python_elaborated/'+bus_line_file, 'w', newline='') as csvfile:

        spamwriter = csv.writer(csvfile, delimiter=',', quoting=csv.QUOTE_MINIMAL)

        # Inserting header
        second_header = []
        second_header.append("bustop_id")
        second_header.append("busline_name")
        spamwriter.writerow(second_header)

        # Inserting rows
        for line in new_file_lines:
            for value in line[0][13].split(','):
                spamwriter.writerow([line[0][0], value])

# Elaborates json and stores elaboration with district id
def elaborate_json(districts, original_file):
    print("[INFO] Processing json file "+original_file)
    new_file_lines = []

    with open('resources/data/01_original/'+original_file) as json_file:
        data = json.load(json_file)

        # Iterating through dataset
        for row in data:

            # Checking if the geometrical information are provided
            if "geo" in row:

                # Creating the point
                location_point = Point(float(row['geo']['latitude']), float(row['geo']['longitude']))

                # Iterate through districts to find the correct match
                district_id = 0
                for i in range(1, len(districts)):
                    if location_point.within(districts[i]):
                        district_id = i
                        break

                # Insert district id inside the row
                if district_id != 0:
                    row['geometry'] = str(location_point)
                    row['district_id'] = district_id
                    new_file_lines.append(row)

    # Destination file - name preparation
    destination_file = original_file.replace("-", "elaborated")
    destination_file = destination_file.replace(" ", "_")

    # Writing rows in new file
    with open('resources/data/02_python_elaborated/'+destination_file, 'w') as json_dest_file:
        json.dump({"poi": new_file_lines}, json_dest_file)

# Elaborates Train Station json differently
def elaborate_train_station_json(districts):
    original_file = "05 - train station.json"
    print("[INFO] Processing json file "+original_file)
    new_file_lines = []

    with open('resources/data/01_original/'+original_file) as json_file:
        data = json.load(json_file)

        # Iterating through dataset
        for row in data['stations']:

            # Checking if the geometrical information are provided
            if "coordinate" in row:

                # Creating the point
                location_point = Point(float(row['coordinate']['x']), float(row['coordinate']['y']))

                # Iterate through districts to find the correct match
                district_id = 0
                for i in range(1, len(districts)):
                    if location_point.within(districts[i]):
                        district_id = i
                        break

                # Insert district id inside the row
                if district_id != 0:
                    row['geometry'] = str(location_point)
                    row['district_id'] = district_id
                    new_file_lines.append(row)

    # Destination file - name preparation
    destination_file = original_file.replace("-", "elaborated")
    destination_file = destination_file.replace(" ", "_")

    # Writing rows in new file
    with open('resources/data/02_python_elaborated/'+destination_file, 'w') as json_dest_file:
        json.dump({"stations": new_file_lines}, json_dest_file)

# Copying the neighbourhood file in the destination folder
def copy_neighbourhoods():
    print("[INFO] Copying neighbourhoods file")
    file = []

    # Reading the neighbourhoods file
    with open('resources/data/01_original/02 - neighbourhood.csv') as csv_file:
        data = csv.reader(csv_file, delimiter=';')

        # Iterating through dataset
        for row in data:
            file.append(row)

    # Writing into the destination file
    with open('resources/data/02_python_elaborated/02_elaborated_neighbourhood.csv', 'w', newline='') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=',', quoting=csv.QUOTE_MINIMAL)

        # Inserting rows
        for line in file:
            spamwriter.writerow(line)
