# Generated by Django 3.0.4 on 2020-03-25 09:54

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('business', '0027_request_address'),
    ]

    operations = [
        migrations.AlterField(
            model_name='business',
            name='address',
            field=models.CharField(default='', max_length=255),
        ),
    ]