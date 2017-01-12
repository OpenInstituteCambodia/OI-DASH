<img src="http://open.org.kh/sites/default/files/logo.gif"/ >
# Open Source Monitoring Platform
OI-DASH is an Open Source Monitoring Platform that allows viewing and analysis of data collected by using the ODK suit.

The OI-DASH database describes the content of the ODK Aggregate database, and it allows the platform to adapt to use that content, which has been collected using ODK Collect.

As the ODK Aggregate database tends to have a large number of not-so-simple tables. A flat view of this database is created in each project, and this is the VIEW of the database to which the OI-DASH makes reference to.

The OI-DASH database is configured by importing and EXCEL format file (.xls) that contains all the information about the data in the view of the ODK Aggregate DB, and how it is used in the dashboard (fields can be edited, used for filters, included as columns of data, etc.). It also describes to what variables can be compared in filters.

The OI-DASH in project-agnostic, the code does not (should not) include any information specific to a project. The platform is installed and an installation procedure creates the view and populates the OI-DASH DB.

## OI-DASH

OI-DASH/Dashboard/Source - Includes the present state of the code for the dashboard.
OI-DASH/Dashboard/Source/Docs - Includes the diagram of how the dashboard database tables relate to each other. 
OI-DASH/Dashboard/Projects - Includes configuration files that make the code in Source work for a project or another (CFS and CFC). It includes a VIEW of ODK Aggregate DB specific for that project and a configuration file that describes the ODK data and how it is used in the Dashboard.
OI-DASH/CFC-Client - Contains the modifications to ODK Collect that are used in this project.
OI-DASH/CFS-Client -  Contains the modifications to ODK Collect that are used in the Child Friendly Schools project.
OI-DASH/Docs contains the general diagram of how the project comes together.


The OI-DASH and its components are developed by the Open Institute, which holds the copyright. It is distributed through an LGPL license, allowing any form of non-commercial and commercial use of the code. The text of the license can be found in: https://www.gnu.org/licenses/lgpl-3.0.html


At this time **OI-DASH** is being used for two projects funded by **UNICEF** in Cambodia

- Monitoring of Child Friendly Schools.
- Monitoring of Residential Care Facilities for children
