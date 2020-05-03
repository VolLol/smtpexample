# Реализация имитации SMTP сервера
## Docs
* https://regex101.com/
* https://javarush.ru/groups/posts/regulyarnye-vyrazheniya-v-java
* https://ru.wikipedia.org/wiki/SMTP
* https://tools.ietf.org/html/rfc821
* https://habr.com/ru/post/358304/
* https://tproger.ru/translations/finite-state-machines-theory-and-implementation/

## Success manual test №1
```
EHLO s1.example.com
250 smtp.example.com Hello
MAIL FROM: <chris@contoso.com>
250 2.1.0 Sender OK
RCPT TO: <kate@fabrikam.com>
250 2.1.5 Recipient OK
DATA
354 Start mail input; end with <CRLF>.<CRLF>
We detail consent in the compliance section of our SMS 101 guide, but the gist is that a contact needs to willingly give you their cell number before you contact them. The examples below apply mostly to new leads and existing customers you might want to upsell.
.
250 2.6.0 Queued mail for delivery
------------------------------------------------
EHLO      = s1.example.com
MAIL FROM = chris@contoso.com
RCPT TO   = kate@fabrikam.com
----------------------DATA----------------------
We detail consent in the compliance section of our SMS 101 guide, but the gist is that a contact needs to willingly give you their cell number before you contact them. The examples below apply mostly to new leads and existing customers you might want to upsell.
.
------------------------------------------------
QUIT
```