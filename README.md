# StonksNews

## Description

Simple app I'm doing in the spare time. The purpose of this app is displaying a list of news related to the global stock market and finance. 
Secondly it displays a list of trending financial instruments (for example stocks, cryptos, currencies) per selected region.

## API

All the information presented is gathered from two sources:
- Marketaux, https://www.marketaux.com/
- YH Finance api (wrapper around the actual yahoo finance api), https://www.yahoofinanceapi.com/

Marketaux provide the list of news while YHfinance provides the information related to stocks.
Both APIs are used in their free version which come with a lot of limiting. You can read about the limitations for Marketaux here, 
https://www.marketaux.com/pricing, while for YHFinance here, https://www.yahoofinanceapi.com/pricing

## Some implementation details
This app uses the repository pattern to provide offline caching in case of absence of network data.
It has been implemented using:
- The data persistence library, Room.
- Using the Retrofit networking library.
- The basic Android Architecture Components, ViewModel, ViewModelFactory, and LiveData.
- Building and launching a coroutine.
- Binding adapters in data binding.
