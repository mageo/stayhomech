# Generated by Django 3.0.4 on 2020-03-19 13:39

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('geodata', '0008_auto_20200315_1940'),
    ]

    operations = [
        migrations.AddField(
            model_name='municipality',
            name='name_de',
            field=models.CharField(max_length=30, null=True),
        ),
        migrations.AddField(
            model_name='municipality',
            name='name_en',
            field=models.CharField(max_length=30, null=True),
        ),
        migrations.AddField(
            model_name='municipality',
            name='name_fr',
            field=models.CharField(max_length=30, null=True),
        ),
        migrations.AddField(
            model_name='municipality',
            name='name_it',
            field=models.CharField(max_length=30, null=True),
        ),
        migrations.AddField(
            model_name='npa',
            name='name_de',
            field=models.CharField(max_length=27, null=True),
        ),
        migrations.AddField(
            model_name='npa',
            name='name_en',
            field=models.CharField(max_length=27, null=True),
        ),
        migrations.AddField(
            model_name='npa',
            name='name_fr',
            field=models.CharField(max_length=27, null=True),
        ),
        migrations.AddField(
            model_name='npa',
            name='name_it',
            field=models.CharField(max_length=27, null=True),
        ),
    ]
