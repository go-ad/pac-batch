package com.cainz;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

/**
 * @see <a href="https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/ec2-iam-roles.html">AWS Default credentials provider chain</a>
 */
public class CIAwsCredentialsProviderChainCondition implements Condition {
    private static final Logger LOG = LoggerFactory.getLogger(CIAwsCredentialsProviderChainCondition.class);
    @Override
    public boolean matches(ConditionContext context) {
        if (System.getenv("CI") == null) {
            LOG.info("CI environment variable not present - Condition fulfilled");
            return true;
        }
        if (System.getProperty("aws.accessKeyId") != null && System.getProperty("aws.secretAccessKey") !=null ) {
            LOG.info("system properties aws.accessKeyId and aws.secretAccessKey present - Condition fulfilled");
            return true;
        }
        if (System.getenv("AWS_ACCESS_KEY_ID") != null && System.getenv("AWS_SECRET_ACCESS_KEY") !=null ) {
            LOG.info("environment variables AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY present - Condition fulfilled");
            return true;
        }
        if (System.getenv("AWS_CONTAINER_CREDENTIALS_RELATIVE_URI") != null) {
            LOG.info("AWS_CONTAINER_CREDENTIALS_RELATIVE_URI environment variable present - Condition fulfilled");
            return true;
        }
        boolean result = System.getenv("HOME") != null && new File(System.getenv("HOME") + "/.aws/credentials").exists();
        if (result) {
            LOG.info("~/.aws/credentials file exists - Condition fulfilled");
        }
        return result;
    }
}
