{\rtf1\ansi\ansicpg1252\cocoartf1404\cocoasubrtf340
{\fonttbl\f0\froman\fcharset0 Times-Roman;}
{\colortbl;\red255\green255\blue255;\red0\green0\blue233;\red0\green0\blue0;}
{\*\listtable{\list\listtemplateid1\listhybrid{\listlevel\levelnfc23\levelnfcn23\leveljc0\leveljcn0\levelfollow0\levelstartat1\levelspace360\levelindent0{\*\levelmarker \{disc\}}{\leveltext\leveltemplateid1\'01\uc0\u8226 ;}{\levelnumbers;}\fi-360\li720\lin720 }{\listname ;}\listid1}}
{\*\listoverridetable{\listoverride\listid1\listoverridecount0\ls1}}
{\info
{\title openinstitute_official / Care For Children / source / README.md \'97 Bitbucket}}\paperw12240\paperh15840\vieww16600\viewh20140\viewkind0
\deftab720
\pard\pardeftab720\sl280\partightenfactor0

\f0\fs24 \cf0 \expnd0\expndtw0\kerning0
\
\
OI-DASH is an Open Source Monitoring Platform that allows viewing and analysis of data collected by using the ODK suit.\
\
The OI-DASH database describes the content of the ODK Aggregate database, and it allows the platform to adapt to use that content, which has been collected using ODK Collect.\
\
As the ODK Aggregate database tends to have a large number of not-so-simple tables. A flat view of this database is created in each project, and this is the VIEW of the database to which the OI-DASH makes reference to.\
\
The OI-DASH database is configured by importing and EXCEL format file (.xls) that contains all the information about the data in the view of the ODK Aggregate DB, and how it is used in the dashboard (fields can be edited, used for filters, included as columns of data, etc.). It also describes to what variables can be compared in filters.\
\
The OI-DASH in project-agnostic, the code does not (should not) include any information specific to a project. The platform is installed and an installation procedure creates the view and populates the OI-DASH DB. \
\pard\pardeftab720\sl280\partightenfactor0
{\field{\*\fldinst{HYPERLINK "file:///openinstitute_official/care-for-children"}}{\fldrslt \cf2 \ul \ulc2  }}\
\pard\pardeftab720\sl560\sa321\partightenfactor0
{\field{\*\fldinst{HYPERLINK "file:///openinstitute_official/care-for-children"}}{\fldrslt 
\b\fs48 \cf2 \ul \ulc2 OI-DASH}}
\b\fs48 \
\pard\pardeftab720\sl280\sa240\partightenfactor0

\b0\fs24 \cf3 \outl0\strokewidth0 \strokec3 OI-DASH/Dashboard/Source - Includes the present state of the code for the dashboard.\
OI-DASH/Dashboard/Source/Docs - Includes the diagram of how the dashboard database tables relate to each other.\uc0\u8232 \
OI-DASH/Dashboard/Projects - Includes configuration files that make the code in Source work for a project or another (CFS and CFC). It includes a VIEW of ODK Aggregate DB specific for that project and a configuration file that describes the ODK data and how it is used in the Dashboard.\
OI-DASH/CFC-Client - Contains the modifications to ODK Collect that are used in this project.\
OI-DASH/CFS-Client -\'a0 Contains the modifications to ODK Collect that are used in the Child Friendly Schools project.\
OI-DASH/Docs contains the general diagram of how the project comes together.\
\pard\tx220\tx720\pardeftab720\li720\fi-720\sl280\partightenfactor0
\ls1\ilvl0\cf0 \outl0\strokewidth0 \
The OI-DASH and its components are developed by the Open Institute, which holds the copyright. It is distributed through an LGPL license, allowing any form of non-commercial and commercial use of the code. The text of the license can be found in: https://www.gnu.org/licenses/lgpl-3.0.html \
\
\
At this time OI-DASH is being used for two projects funded by UNICEF in Cambodia\
\
- Monitoring of Child Friendly Schools.\
- Monitoring of Residential Care Facilities for children\
\
}