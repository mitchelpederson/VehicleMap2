# -*- coding: utf-8 -*-
# Generated by Django 1.10.5 on 2017-01-25 21:23
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Car',
            fields=[
                ('given_id', models.CharField(max_length=64, primary_key=True, serialize=False)),
                ('last_update', models.IntegerField()),
                ('last_request', models.IntegerField(null=True)),
                ('lat', models.FloatField(null=True)),
                ('long', models.FloatField(null=True)),
                ('speed', models.FloatField(null=True)),
                ('bearing', models.FloatField(null=True)),
            ],
        ),
    ]