Timesheet management
====================

Implement the application for managing the timesheets in a small company.

All classes are in the package `timesheet`. The facade class is `Timesheet`. The class `TestApp` in the package `example` contains an example. Exceptions are thrown through the class `TimesheetException`.

It is possible to access [Java API documentation](http://softeng.polito.it/courses/docs/api/index.html).

R1 Calendar
-----------

The method `setHolidays(int... holidays)` set the holidays in the calendar. Holidays are indicated by an array of integers representing the days in the year. That is, **1** is January, 1st; and **365** is December, 31st. Leap years are not considered. Numbers less than **1** should be ignored. The same day may be specified multiple times. After holidays have been set, subsequent calls to the method will be ignored.

The method `isHoliday(int day)` returns a Boolean value: **true** if `day` is a holiday; **false** if not.

The method `setFirstWeekDay(int weekDay)` gets as parameter an integer representing the day of the week of January, 1st. The number **0** stands for Sunday, **1** for Monday, etc. If `weekDay` is less than **0** or greater than **6**, the method throws the exception `TimesheetException`. If invoked multiple times, the method overwrites the value of the first day of the year. By default, the first day of the year is assumed to be Monday (**1**).

The method `getWeekDay(int day)` returns the day of the week (**0-6**) corresponding to the given day of the year (**1-365**). For example, if the first day of the year is Friday (i.e., **5**), the method called with `day = 2` will return **6** (Saturday). If `day` is less than **1**, the method throws the exception `TimesheetException`.

R2 Projects
-----------

The method `createProject(String projectName, int maxHours)` creates a new project with a maximum number of allocated hours. Project names are unique. If `maxHours` is negative, the method throws the exception `TimesheetException`. It is possible to modify the maximum number of hours allocated to a specific project `projectName` by calling the method multiple times.

The method `getProjects()` returns all known projects as a `List` of strings. Projects in the list are sorted in descending order by the maximum number of allocated hours, then alphabetically (i.e., in ascending order) by the project name. The format of the description strings in the list is: `"{projectName}: {maxHours}"`.

The method `createActivity(String projectName, String activityName)` creates a new activity linked to a given project. Activity names inside the same project can be assumed to be unique. If the project has not been previously defined, the method throws the exception `TimesheetException`.

The method `closeActivity(String projectName, String activityName)` marks as _completed_ the given activity inside the given project. Initially all activities are marked as _not completed_. If the project has not been previously defined, or the activity does not exists in the project, the method throws the exception `TimesheetException`.

The method `getOpenActivities(String projectName)` returns a `List` of activities of the given project that have not yet been marked as _completed_. Activities in the list are sorted alphabetically. If the project has not been previously defined, the method throws the exception `TimesheetException`.

R3 Workers
----------

The method `createProfile(int... workHours)` creates a new hourly profile, that is, the maximum number of hours that one may work in each day of the week. The array of integers must contain exactly 7 values, one for each day of the week, with the first referring to Sunday and the last to Saturday. For instance, if `workHours` is **\[0, 8, 8, 8, 8, 8, 0\]**, the worker may be exploited for eight hours Monday to Friday, and can rest in the weekend. The method returns a string with the random unique identifier associated to the profile. If `workHours` does not contain seven values, the method throws the exception `TimesheetException`.

The method `getProfile(String profileID)` returns a hourly profile given its unique identifier as a string. The format follows this example: “Sun: 0; Mon: 8; Tue: 8; Wed: 8; Thu: 8; Fri: 8; Sat: 0”. If the hourly profile `profileID` has not been previously created, the method throws the exception `TimesheetException`.

The method `createWorker(String name, String surname, String profileID)` creates a worker with a definite hourly profile. The method returns a string with the random unique identifier associated to the worker. If the hourly profile `profileID` has not been previously created, the method throws the exception `TimesheetException`.

The method `getWorker(String workerID)` returns a string describing a worker given the worker’s unique identifier. The string is in the format `"{name} {surname} ({profileString})"`, where `"{profileString}"` is the format used by `getWorkerProfile(String profileID)`. If the worker `workerID` has not been previously created, the method throws the exception `TimesheetException`.

**Hint:** a method for generating the unique identifiers can be chosen by the developer, but it must guarantee the uniqueness in each instance of the class `Timesheet`.

R4 Report
---------

The method `addReport(String workerID, String projectName, String activityName, int day, int workedHours)` adds a new entry to the timesheet of a given worker. It is required to verify all the following conditions, and if they are not all satisfied the method throws the exception `TimesheetException`:

*   the worker identifier should exist;
*   the day should be greater than **0** and less than **366**, and not a holiday;
*   the number of hours should not be negative;
*   the number of hours should be compatible with the hourly profile of the worker;
*   the project should exist;
*   the activity should exist inside the project;
*   the total number of hours allocated to the project should be less than its maximum number of hours;
*   The activity should not be completed.

The method `getProjectHours(String projectName)` returns the total number of reported hours associated to the given project. If the project has not been previously defined, the method throws the exception `TimesheetException`.

The method `getWorkedHoursPerDay(String workerID, int day)` returns the total number of reported hours by the given worker in the given day of the year. If the worker has not been previously defined, the day is less than **1**, the method throws the exception `TimesheetException`.

R5 Statistiche
--------------

The method `countActivitiesPerWorker()` returns a `Map` that maps each worker to all his distinct activities, that is, the activities where there is at least **1** hour reported. Please note that activities name are unique only inside a project: activities in different projects must be considered distinct even if they have the same name.

The method `getRemainingHoursPerProject()` returns a `Map` that maps each project to the number of hours still available, that is, the difference between the maximum number of hours and the reported ones.

The method `getHoursPerActivityPerProject()` returns a `Map` that maps each project to another `Map`. This second collection is mapping each activity of the project to the total number of hours currently reported.