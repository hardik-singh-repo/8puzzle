.PHONY: run test build clean

AlGS_JAR:= ".:lib/algs4.jar"
JUNIT_JAR:= lib/junit-platform-console-standalone-1.9.3.jar
BUILD_DIR:= build

build: clean
	@mkdir -p $(BUILD_DIR)
	@javac -cp $(AlGS_JAR) src/*.java -d $(BUILD_DIR)

run: build 
	 @java -cp $(AlGS_JAR) src/$(CLASS) $(ARGS)

clean: 
	@rm -rf $(BUILD_DIR)/*

test: build 
	  @javac -cp "$(JUNIT_JAR):$(BUILD_DIR)" test/*.java -d $(BUILD_DIR)
	  @java -jar $(JUNIT_JAR) -cp $(BUILD_DIR) --scan-class-path;
	  
