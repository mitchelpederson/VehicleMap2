from django.conf.urls import url
import seniordesign.views as views

urlpatterns = [
    # GET with optional "given_id" param
    url(r'getall/', views.get_all),
    # POST with params: given_id, lat, long, speed, bearing
    url(r'update/', views.update_pos)
]
