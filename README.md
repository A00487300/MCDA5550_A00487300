# MCDA5550_A00487300

# Hotel Reservation API

This is a RESTful API built with Django + Django Rest Framework, deployed on AWS EC2.

## Run

1. Visit: `http://3.99.216.105/api/hotels/`
2. Visit: `http://3.99.216.105/api/reservationConfirmation/`

## Use with Postman

- **GET Hotels**
  - Method: `GET` and `POST`
  - URL: `http://<your-ec2-ip-or-domain>/api/hotels/`

- **POST Reservation**
  - Method: `GET` and `POST`
  - URL: `http://<your-ec2-ip-or-domain>/api/reservationConfirmation/`
  - Post Body (JSON):
    ```json
    {
    "hotel_name": test1,
    "checkin": 2025-12-11,
    "checkout": 2025-12-12,
    "guests_list": [
    {"guest_name":"Alice", "gender":"Female"},
    {"guest_name":"Bob", "gender":"Male"}
    ]
    }
    ```

## Functionality: Postman test

    • Sending a GET request with Postman can successfully see /api/hotels/

    • Sending a POST request with Postman to /api/reservationConfirmation/ can add a reservation

    • If an error occurs, return a JSON error message (such as missing fields, invalid data )


## Successfully integrated with Android native app

    • Can be requested normally by Android native app

    • The returned data structure is consistent and clear

    • Support cross-domain (CORS): corsheaders can be installed






