from time import time
import csv

from django.http import HttpResponse, JsonResponse
import django.core.serializers as serializers
from django.forms.models import model_to_dict

from seniordesign.models import Car


def get_all(req):
    threshold = 30*60 # seconds
    id = req.GET.get('given_id', None)

    data = None
    if id:
	try:
            car = Car.objects.get(given_id=id)
	    data = [model_to_dict(item) for item in list(Car.objects.filter(last_update__gt=max(time()-threshold, car.last_request)))]
	    car.last_request = time()
	except Car.DoesNotExist:
	    car = {'last_request': time(), 'given_id': id}
	    car = Car(**car)
	car.save()

    if data is None:
	data = [model_to_dict(item) for item in list(Car.objects.filter(last_update__gt=time()-threshold))]

    return JsonResponse(data, safe=False)


def update_pos(req):
    given_id = req.POST.get('given_id', None)
    if given_id is None:
        user_car = Car()
    else:
        user_car = Car.objects.filter(given_id=given_id)
        if not user_car:
            user_car = Car()
            user_car.given_id = given_id
        else:
            user_car = user_car[0]

    user_car.lat = req.POST.get('lat', None) or user_car.lat
    user_car.long = req.POST.get('long', None) or user_car.long
    user_car.speed = req.POST.get('speed', None) or user_car.speed
    user_car.bearing = req.POST.get('bearing', None) or user_car.bearing
    user_car.last_update = time()
    user_car.save()

    with open('out.log', 'a') as f:
        writer = csv.writer(f, delimiter=',')
        writer.writerow([user_car.given_id, user_car.lat, user_car.long, user_car.speed, user_car.bearing])

    return JsonResponse(model_to_dict(user_car))
