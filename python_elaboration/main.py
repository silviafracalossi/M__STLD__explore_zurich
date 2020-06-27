import csv
import shapely

from shapely.geometry import Point, Polygon
from districts_loader import load_districts
from files_elaborator import elaborate_csv, elaborate_json, elaborate_train_station_json, copy_neighbourhoods, elaborate_bus_csv

districts = load_districts()                                # 01 - district.csv
copy_neighbourhoods()                                       # 02 - neighbourhood.csv
elaborate_bus_csv(districts)
elaborate_train_station_json(districts)                     # 05 - train station.csv
elaborate_csv(districts, "06 - car parking.csv", 60)
elaborate_csv(districts, "07 - bike parking.csv", 12)
elaborate_json(districts, "08 - restaurant.json")
elaborate_json(districts, "09 - bar.json")
elaborate_json(districts, "10 - museum.json")
elaborate_json(districts, "11 - attraction.json")
elaborate_json(districts, "12 - shopping.json")
print("[INFO] Task completed!")
