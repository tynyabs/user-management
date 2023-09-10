# Use a base image from Docker Hub
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the application source code to the container
COPY . /app

# Run any necessary commands to set up the environment or dependencies
RUN apt-get 

# Expose any necessary ports
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "myapp.jar"]
