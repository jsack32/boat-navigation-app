run: NavigationApp.class PortDotDW.class ExclusiveDijkstraGraphAE.class NavigationAppFrontendFD.class NavigationAppBackendBD.class
	java NavigationApp

runTests: runAlgorithmEngineerTests runDataWranglerTests runFrontendDeveloperTests runBackendDeveloperTests

NavigationApp.class: NavigationApp.java
	javac NavigationApp.java

runDataWranglerTests: DataWranglerTests.class PortDotDW.class 
	java -jar junit5.jar -cp . --select-class=DataWranglerTests

PortDotDW.class: PortDotDW.java
	javac -cp .:junit5.jar DataWranglerTests.java

DataWranglerTests.class: DataWranglerTests.java ExclusiveDijkstraGraphAE.class NavigationAppBackendFD.class
	javac -cp .:junit5.jar DataWranglerTests.java

runAlgorithmEngineerTests: AlgorithmEngineerTests.class ExclusiveDijkstraGraphAE.class
	java -jar junit5.jar -cp . --select-class=AlgorithmEngineerTests

AlgorithmEngineerTests.class: AlgorithmEngineerTests.java
	javac -cp .:junit5.jar AlgorithmEngineerTests.java

ExclusiveDijkstraGraphAE.class: ExclusiveDijkstraGraphAE.java
	javac -cp .:junit5.jar ExclusiveDijkstraGraphAE.java

runBackendDeveloperTests: BackendDeveloperTests.class
	java -jar ./junit5.jar -cp . --select-class=BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac -cp .:junit5.jar BackendDeveloperTests.java

NavigationAppBackendBD.class: NavigationAppBackendBD.java
	javac NavigationAppBackendBD.java

runFrontendDeveloperTests: FrontendDeveloperTests.class NavigationAppFrontendFD.class NavigationAppBackendFD.class ExclusiveDijkstraGraphFD.class
	java -jar ./junit5.jar -cp . --select-class=FrontendDeveloperTests

FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac -cp .:junit5.jar FrontendDeveloperTests.java

NavigationAppFrontendFD.class: NavigationAppFrontendFD.java
	javac NavigationAppFrontendFD.java

NavigationAppBackendFD.class: NavigationAppBackendFD.java
	javac NavigationAppBackendFD.java

ExclusiveDijkstraGraphFD.class: ExclusiveDijkstraGraphFD.java
	javac ExclusiveDijkstraGraphFD.java

clean:
	rm *.class
