# senda-webcrawler
The crawler should be limited to one domain - so when crawling Sedna.com it would crawl all pages within the domain, but not follow external links, such as LinkedIn and Twitter accounts. Given a URL, it should output a list of all pages found at that domain

# use postman / cuRl to post a url (POST method)
 -> http://localhost:8080/api/crawl

 -> Request body: 
 {
 "url": "https://www.realconnectioncarpetcleaning.com/"
 }
 
# This a web based application that is based on spring boot with implementation on the backend. 
