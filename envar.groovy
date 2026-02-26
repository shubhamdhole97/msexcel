// envar.groovy

 

// Project name

def projectName = 'msexcel'

 

// Environment configurations

def environmentConfig = [

    dev: [serverIp: '10.10.9.38', profile: 'local', imageTag: 'D01'],

    qa: [serverIp: '10.10.9.41', profile: 'qa', imageTag: 'D01R01'],

    uat: [serverIp: '10.10.14.11', profile: 'uat', imageTag: 'D01R02']

]

 

// Function to get environment configuration

def getEnvironmentConfig(env) {

    return environmentConfig[env]

}

 
