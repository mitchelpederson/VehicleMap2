from django.db import models


class Car(models.Model):
    given_id = models.CharField(max_length=64, primary_key=True)
    last_update = models.IntegerField(null=True)
    last_request = models.IntegerField(null=True)
    lat = models.FloatField(null=True)
    long = models.FloatField(null=True)
    speed = models.FloatField(null=True)
    bearing = models.FloatField(null=True)