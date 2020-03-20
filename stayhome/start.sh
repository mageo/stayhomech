#!/usr/bin/env bash

# Apply migrations
python manage.py migrate --noinput

# Create admin user
python manage.py createsuperuser --no-input

# Import data, only with new repo
# Do it manually in container later if necessary
# This is to avoid a very long startup delay
if [ -f "datasets/post_data.csv.bz2" ]; then

    # Uncompress Swiss Post dataset
    cd datasets
    bunzip2 post_data.csv.bz2
    cd ..

    # Import Swiss Post dataset - Creates municipalities and postal codes (NPA)
    python manage.py geo_import datasets/post_data.csv --debug

    # Import list of Swiss cantons
    python manage.py geo_import_cantons datasets/cantons.csv

    # Import list of Swiss districts
    python manage.py geo_import_districts datasets/districts.csv

    # Attach municipalities to districts
    python manage.py geo_import_municipalities datasets/municipalities.csv

fi

# Compile language files
python manage.py compilemessages

# Statics
python manage.py collectstatic --noinput

# Translations
python manage.py update_translation_fields

# Run application
gunicorn -b 0.0.0.0:8000 stayhome.wsgi