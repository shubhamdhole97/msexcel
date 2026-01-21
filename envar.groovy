// envar.groovy

 

// Project name

def projectName = 'msexcel'

 

// Environment configurations

def environmentConfig = [

    dev: [serverIp: '34.132.151.160', profile: 'local', imageTag: 'D01'],

    qa: [serverIp: '136.112.187.111', profile: 'qa', imageTag: 'D01R01'],

    // uat: [serverIp: '10.10.14.11', profile: 'uat', imageTag: 'D01R02']

]

 

// Function to get environment configuration

def getEnvironmentConfig(env) {

    return environmentConfig[env]

}

 
