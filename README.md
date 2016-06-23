#Product Recommendation #

This project searches product through Walmart API and recommends 10 products.

Technologies Used:
- Java
- Concurrency
- Logging
- Unit tests
- Basic sentiment analysis method

Because of time constraints I can not implement through test cases and Caching mechnism.

###Setup###
1. Install java from [this link](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

2. Install Maven build tool from [this link](https://maven.apache.org/install.html).
	If you are using Ubuntu then `sudo apt-get isntall maven` will work.

3. Create `config.properties` file in project root folder with following:


NOTE: replace `<your-api-key>` with correct Walmart API key.
```config
DEBUG = false
URL = http://api.walmartlabs.com/v1/
SEARCH_ENDPOINT = search
RECOMMENDATION_ENDPOINT = nbp
REVIEW_ENDPOINT = reviews
API_KEY = <your api key>
POSITIVE_KEYWORDS = good, very good, best, like, love, fits, excellent, helpful, top, wonderful, recommend, perfect
NEGATIVE_KEYWORDS = bad, delay, not good, don't like, do not like, doesn't fit, does not fit, don't buy, do not buy, problem, broke, poor, break, do not recommend

```
Folder structure![Folder Image](https://github.com/jaympatel/product-recommendation/blob/master/img/folder-structure.png)

3. To build binary file:
```bash
mvn clean package 
```

### Run ###

To run this Utility type following in terminal:
```bash
java -cp target/product-recommendation-1.0-SNAPSHOT.jar com.walmart.app.App
```
Follow on screen isntructions.
