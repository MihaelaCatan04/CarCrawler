# Car Scraper and Analyzer

This Java project extracts and analyzes car data from
https://999.md/ro.\
The application uses **HTTP requests**, **HTML parsing**, and **GraphQL
queries** to dynamically retrieve available cars, models, and
generations, and integrates with a **Telegram bot** for interaction.

Instead of browser automation or CSV storage, the program processes data
directly in memory and returns filtered results with calculated price
statistics.

------------------------------------------------------------------------

## Features

-   Fetch available **car brands** using HTTP requests and HTML parsing
-   Retrieve **models and generations** dynamically through GraphQL
-   Integrate with a Telegram bot for selecting filters
-   Extract car details:
  -   Name
  -   Model
  -   Generation
  -   Year
  -   Mileage
  -   Price
  -   Listing Link
-   Convert prices to Euro (supports MDL and USD)
-   Filter cars based on configuration
-   Calculate statistics:
  -   Lowest price
  -   Highest price
  -   Average price

------------------------------------------------------------------------

## How It Works

1.  The program sends an HTTP request to 999.md and parses the HTML to
    extract available car brands.
2.  After selecting a car, a GraphQL request retrieves all available
    models and generations.
3.  The Telegram bot displays selection options using inline keyboards.
4.  Listings are processed in memory and filtered according to
    configuration.
5.  The program calculates and outputs price statistics.

------------------------------------------------------------------------

## Getting Started

### Prerequisites

-   Java 17+
-   Maven or Gradle
-   Telegram Bot Token
-   `.env` file with configuration:

```
BOT_TOKEN =
CREATOR_ID =
BOT_USERNAME =
```
------------------------------------------------------------------------

## Example Output

    Lowest price: 5000€
    Lowest link: https://999.md/ro/...
    Highest price: 20000€
    Highest link: https://999.md/ro/...
    Average price: 12000€

------------------------------------------------------------------------

### Elapsed Time
For the default configuration, the elapsed time for scraping and filtering the cars is:
<img width="676" height="50" alt="Screenshot From 2026-02-06 15-07-10" src="https://github.com/user-attachments/assets/a8155049-21d3-46cc-b4b4-e3e452fa47d1" />

------------------------------------------------------------------------

## Limitations

-   **Fixed currency conversion rates**\
    MDL→EUR and USD→EUR conversions use static rates and are not updated
    in real time.

-   **Telegram inline keyboard limit**\
    The Telegram bot cannot display all 124 available car brands at
    once, so only the first 100 are shown.

-   **Website structure dependency**\
    HTML parsing relies on the current structure of 999.md. Changes to
    the page script may require updates to selectors or parsing logic.

