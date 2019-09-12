# TASKMASTER

##Links to Back End Routes
* Get All tasks
  * GET http://taskmaster-dev.us-east-1.elasticbeanstalk.com/api/v1/tasks/ 
* Get All Tasks for user
  * http://taskmaster-dev.us-east-1.elasticbeanstalk.com/api/v1/users/{name}/tasks
* Create a Task
  * POST http://taskmaster-dev.us-east-1.elasticbeanstalk.com/api/v1/tasks
  * Body takes a titel, description, and assignee
* Adavance a tasks state
  * PUT http://taskmaster-dev.us-east-1.elasticbeanstalk.com/api/v1/tasks/{id}/state
* Assign a task to a user
  * Put http://taskmaster-dev.us-east-1.elasticbeanstalk.com/api/v1/tasks/{id}/assign/{assignee}
