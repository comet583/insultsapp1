## Java Port of Getting Started with OpenShift Book##

You can download Getting Started with OpenShift book from [here](https://www.openshift.com/blogs/announcing-a-new-book-getting-started-with-openshift-a-guide-for-impatient-beginners).

### Create OpenShift Application###

Open a new command-line terminal, navigate to convinient location, and then create a new OpenShift application using rhc command-line tool.

```
$ rhc app-create insultapp jbosseap postgresql-9 --from-code https://github.com/shekhargulati/insultapp.git
```

After application creation, SSH into application gear using rhc ssh command.

```
$ rhc ssh --app insultapp
```

And run the following commnad to create database schema with input data.
```
$ psql -f $OPENSHIFT_REPO_DIR/import.sql
```

Open the application in web browser http://insultapp-{{domain-name}}.rhcloud.com. Please replace {{domain-name}} with your OpenShift account domain name.

### Application Versioning##

The application has three git tags that you can find here https://github.com/shekhargulati/insultapp/releases.

1. AppTemplate : This is the starter template application created by OpenShift. Just a simple hello world application.

2. InMemoryInsults : This version finds insults from java.util.List. 

3. DatabaseInsults : This version finds insults from PostgreSQL database.