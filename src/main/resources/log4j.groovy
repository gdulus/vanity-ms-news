// define all appender
def rootLogger = "INFO"

// console logger
log4j.appender.stdout = "org.apache.log4j.ConsoleAppender"
log4j.appender."stdout.layout" = "org.apache.log4j.PatternLayout"
rootLogger += ", stdout"

// file logger
if (System.getProperty('log4j.file.location')) {
    log4j.appender.rfa = 'org.apache.log4j.RollingFileAppender'
    log4j.appender.'rfa.maxFileSize' = '100MB'
    log4j.appender.'rfa.file' = System.getProperty('log4j.file.location')
    log4j.appender.'rfa.maxBackupIndex' = 1
    log4j.appender.'rfa.layout' = 'org.apache.log4j.PatternLayout'
    log4j.appender.'rfa.layout.ConversionPattern' = '%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n'
    rootLogger += ", rfa"
}

log4j.rootLogger = rootLogger