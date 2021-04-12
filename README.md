# campsite

# Technoligies used
Spring boot, java, in memory database

# Constraints
* The campsite can be reserved for max 3 days.
* The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.
* Reservations can be cancelled anytime.
* For sake of simplicity assume the check-in & check-out time is 12:00 AM.

# Requirements
* The users will need to find out when the campsite is available. So the system should expose an API to provide information of the availability of the campsite for    a given date range with the default being 1 month.
* Provide an end point for reserving the campsite. The user will provide his/her email & full name at the time of reserving the campsite along with intended arrival  date and departure date. Return a unique booking identifier back to the caller if the reservation is successful.
* The unique booking identifier can be used to modify or cancel the reservation later on. Provide appropriate end point(s) to allow modification/cancellation of an existing reservation.
* Due to the popularity of the campsite, there is a high likelihood of multiple users attempting to reserve the campsite for the same/overlapping date(s).
* Demonstrate with appropriate test cases that the system can gracefully handle concurrent requests to reserve the campsite.
* Provide appropriate error messages to the caller to indicate the error cases.
* The system should be able to handle large volume of requests for getting the campsite availability.
* There are no restrictions on how reservations are stored as long as system constraints are not violated.

# API Docs localhost:8080/swagger-ui.html

# Due to the popularity of the campsite, there is a high likelihood of multiple users attempting to reserve the campsite for the same/overlapping date(s).
* Complexity in this task achieve booking ticket without overlapping and this can be achieved in different ways 
  * thread pool/MQ 
  * pessimistic locking
  * inmemory cache(collection) I used this approach
* To avoid reserving the campsite for the same/overlapping date, inmemory List is used when ever we try to book a site date, that date is saved in that list and this is list is synchronized.


