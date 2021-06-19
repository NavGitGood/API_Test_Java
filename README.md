# API_Test_Java

API testing framework using:
1. RestAssured
2. Hamcrest matchers
3. ExtentReports

* Sample tests are written to test following api:

    https://order-pizza-api-python3-7.herokuapp.com/api

* To get the code for API, use

    git clone https://github.com/NavGitGood/order-pizza-api.git

* To use this framework, clone from github

    git clone https://github.com/NavGitGood/API_Test_Java.git

Before running the set, make sure to set following environment variable
* **pizza_api_pass**: test

Following TestNg xml files are present:

1. APIRequestTest.xml (6 tests, should pass always)
2. getTest.xml (3 tests, will always fail)

To run from command prompt, navigate to root directory of the project, and run following:

mvn -DsuiteXmlFile="src/test/resources/APIRequestTest.xml" test

Report will be generated in `/output` folder as `ExtentReport.html` overriding any older report 
(to generate report with a new name, update `src/main/java/setup/ExtentReportSetup.java` to use a new name 
for each run (can append timestamp))

Please Note that tests are intentionally made dependent to showcase use of **dependsOnMethods**