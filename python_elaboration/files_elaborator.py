import csv
import json
import shapely
import geopandas as gpd

from shapely.geometry import Point, Polygon
from shape_creator import string_to_point

# Elaborates csv and stores elaboration with district id
def elaborate_csv(districts, original_file, id_point_field, id_secondary_point_field=0):
    print("[INFO] Processing csv file "+original_file)
    new_file_lines = []

    # Reading the district csv
    with open('../data/original/'+original_file) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        header = ""

        # Iterating the csv
        for row in csv_reader:

            # Header
            if header == "":
                header = row
                header[0] = header[0].replace("\"","")

            # Rows
            else:

                # Convert location into point
                location_point = ""
                if id_secondary_point_field == 0:
                    location_point = string_to_point(row[id_point_field])
                else:
                    location_point = Point(float(row[id_point_field]), float(row[id_secondary_point_field]))

                # Iterate through districts to find the correct match
                district_id = 0
                for i in range(1, len(districts)):
                    if location_point.within(districts[i]):
                        district_id = i
                        break

                # Insert district id inside the row
                if district_id != 0:
                    row.append(district_id)
                    new_file_lines.append([row])

    # Destination file - name preparation
    destination_file = original_file.replace("-", "elaborated")
    destination_file = destination_file.replace(" ", "_")

    # Writing into the destination file
    with open('../data/elaborated/'+destination_file, 'w', newline='') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=',', quoting=csv.QUOTE_MINIMAL)

        # Inserting header
        header.append("district_id")
        spamwriter.writerow(header)

        # Inserting rows
        for line in new_file_lines:
            spamwriter.writerow(line[0])

# Elaborates json and stores elaboration with district id
def elaborate_json(districts, original_file):
    print("[INFO] Processing json file "+original_file)
    new_file_lines = []

    with open('../data/original/'+original_file) as json_file:
        data = json.load(json_file)

        # Iterating through dataset
        for row in data:

            # Checking if the geometrical information are provided
            if "geo" in row:

                # Creating the point
                latitude = float(row['geo']['latitude'])
                longitude = float(row['geo']['longitude'])

                # Converting the point in the used format
                geo_df = gpd.GeoDataFrame([],
                    geometry=gpd.GeoSeries(Point(longitude, latitude)))
                geo_df.crs = 'epsg:4326'
                geo_df = geo_df.to_crs('epsg:2056')

                # Extracting the location point
                location_point = geo_df['geometry'].iloc[0]

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
                    new_file_lines.append([row])

    # Destination file - name preparation
    destination_file = original_file.replace("-", "elaborated")
    destination_file = destination_file.replace(" ", "_")

    # Writing rows in new file
    with open('../data/elaborated/'+destination_file, 'w') as json_dest_file:
        for row in new_file_lines:
            json.dump(row, json_dest_file)

# Elaborates Train Station json differently
def elaborate_train_station_json(districts):
    original_file = "05 - train station.json"
    print("[INFO] Processing json file "+original_file)
    new_file_lines = []

    with open('../data/original/'+original_file) as json_file:
        data = json.load(json_file)

        # Iterating through dataset
        for row in data['stations']:

            # Checking if the geometrical information are provided
            if "coordinate" in row:

                # Creating the point
                latitude = float(row['coordinate']['x'])
                longitude = float(row['coordinate']['y'])

                # Converting the point in the used format
                geo_df = gpd.GeoDataFrame([],
                    geometry=gpd.GeoSeries(Point(longitude, latitude)))
                geo_df.crs = 'epsg:4326'
                geo_df = geo_df.to_crs('epsg:2056')

                # Extracting the location point
                location_point = geo_df['geometry'].iloc[0]

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
                    new_file_lines.append([row])

    # Destination file - name preparation
    destination_file = original_file.replace("-", "elaborated")
    destination_file = destination_file.replace(" ", "_")

    # Writing rows in new file
    with open('../data/elaborated/'+destination_file, 'w') as json_dest_file:
        for row in new_file_lines:
            json.dump(row, json_dest_file)
