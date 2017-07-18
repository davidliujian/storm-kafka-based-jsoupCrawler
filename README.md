# storm-kafka-based-jsoupCrawler
爬取一个新闻网站的二级站点，如网易军事。根据二级站点的url将所有新闻的url爬取下来发送到kafka，从kafka取出新闻url，与数据库存储的爬过的url比对，排重。爬取新闻。存储到数据库。
