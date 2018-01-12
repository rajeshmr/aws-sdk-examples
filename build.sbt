name := "aws-sdk-examples"

version := "1.0"

scalaVersion := "2.12.4"

unmanagedClasspath in Test += baseDirectory.value / "special-resources"



libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.256"
libraryDependencies += "joda-time" % "joda-time" % "2.9.9"
