# Generated by Django 3.0.4 on 2020-03-25 09:35

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('business', '0025_auto_20200324_1233'),
    ]

    operations = [
        migrations.AddField(
            model_name='business',
            name='address',
            field=models.CharField(blank=True, max_length=255),
        ),
    ]
