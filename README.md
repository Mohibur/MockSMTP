# MockSMTP

Written to Make Testing easier that handle emails

# Http Server Info
* Bind Addres: localhost
* Bind Port: 8500

# Http commands

Accept only *GET*

* Start SMTP Server

```sh
# default localhost:2525
curl http://localhost:8500/toggol
# result
{"success":true,"status":true}

curl http://localhost:8850/toggol?hostname=localhost&port=2525
# result
{"success":true,"status":false}
```

* Get SMTP server status

```sh
curl http:localhost:8850/status

# response
{"success":true,"status":true}
```

* Get all email information

```sh
curl "http://localhost:8500/messages"

# response
{"success":true,"data":{...}}

```
* Read a signle email, this will also update as read by tester

```sh
curl "http://localhost:8500/messages?index=0&messageId=%3C992136656.0.1587882808639.JavaMail.johny@johny%3E"
# result
{"success":true,"data":{"0":{"readByTester":false,"from":"NoReply-JD \u003cjohny@example.com\u003e","to":"other@mail.com","subject":"Subject to change","mailBody":"\nWe are giong to change the whole idea of living\r\n","date":"Apr 26, 2020, 3:33:28 PM","messageId":"\u003c992136656.0.1587882808639.JavaMail.johny@johny\u003e","mimeVersion":"1.0","index":0,"attachments":{},"attachement":false,"contentType":"text/plain; charset\u003dUTF-8","contentTransferEncoding":"7bit","format":"flowed"}}}
```

* Remove email

```sh
curl "http://localhost:8500/delete"
# response
{"success":false}

 curl "http://localhost:8500/delete?index=0&messageId=%3C992136656.0.1587882808639.JavaMail.johny@johny%3E"
{"success":true}


```

# Missing Options
Attachment handling
