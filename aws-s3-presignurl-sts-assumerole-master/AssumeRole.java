public class AssumeRole {
    static AWSSecurityTokenService stsClient = null;

    static {
        stsClient = AWSSecurityTokenServiceClientBuilder.standard().withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).withRegion("us-east-1").build();
    }

    static Credentials getKeys(String roleName, String sessionName, int duration) {
        AssumeRoleRequest roleRequest = new AssumeRoleRequest().withRoleArn(roleARN)
                .withRoleSessionName(roleSessionName).withDurationSeconds(duration);
        AssumeRoleResult assumeResult = stsClient.assumeRole(roleRequest);
        Credentials temporaryCredentials = assumeResult.getCredentials();
    }

    public static void main(String[] args) {
        Credentials temporaryCredentials = getKeys("role_name", "session_name", 36000);
        System.out.println("ACCESS_KEY_ID ===> " + temporaryCredentials.getAccessKeyId());
        System.out.println("SECRET_ACCESS_kEY ===> " + temporaryCredentials.getSecretAccessKey());
        System.out.println("SESSION_TOKEN ===> " + temporaryCredentials.getSessionToken());

    }
}