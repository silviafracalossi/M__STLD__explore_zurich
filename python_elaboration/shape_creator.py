import csv
import shapely
from shapely.geometry import Point, Polygon

# Converting string coordinates into a polygon
def string_to_polygon(polygon_text):
    result = []

    polygon_text = polygon_text.replace("POLYGON ", "").replace("((", "").replace("))", "")

    # Iterating through each polygon point
    for point in polygon_text.split(","):

        # Splitting the two coordinate values
        coordinates = point.split(" ")

        # Storing coordinate as tuple
        current_tuple = []
        current_tuple.append(float(coordinates[0]))
        current_tuple.append(float(coordinates[1]))

        # Appending coordinate to result list
        result.append(tuple(current_tuple))

    return Polygon(result)


# Converting string coordinates into a point
def string_to_point(point_text):

    point_text = point_text.replace("POINT ", "").replace("(", "").replace(")", "")
    coordinates = point_text.split(" ")

    return Point(float(coordinates[0]), float(coordinates[1]))
