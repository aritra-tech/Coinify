# Coinify

[![Build](https://github.com/aritra-tech/Coinify/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/aritra-tech/Coinify/actions/workflows/build.yml)

Coinify is a multiplatform app built by using Kotlin and Compose Multiplatform that provides real-time cryptocurrency data using the [CoinMarketCap](https://coinmarketcap.com/) API. It allows you to search for cryptocurrency data seamlessly across desktops (Windows, macOS), Android, and iOS.

## Built with 🛠️

- [Kotlin Multiplatform](https://kotlinlang.org/lp/multiplatform/)
- [KTOR](https://ktor.io/)
- [Koin](https://insert-koin.io/)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Navigation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html)
- [ViewModel](https://www.jetbrains.com/help/kotlin-multiplatform-dev/whats-new-compose-eap.html#lifecycle-library)
- [KotlinX Serialization](https://kotlinlang.org/docs/serialization.html)
- [Coroutines](https://discuss.kotlinlang.org/t/coroutines-with-multiplatform-projects/18006)
- [Kermit](https://github.com/touchlab/Kermit)
- [AAY-chart](https://github.com/TheChance101/AAY-chart)
- [BuildKonfig](https://github.com/yshrsmz/BuildKonfig)

## Development 🛠️

### Pre-requisites

- Java JDK 17+
- Latest stable version of Android Studio IDE
- Latest XCode (_for iOS_)
- CoinMarketCap API Key (_Get it from [here](https://coinmarketcap.com/api/)_)

### Setup

- Clone this repository.
- Open in the _latest version_ of Android Studio.
- Place your CoinMarketCap API key in `local.properties` file as `API_KEY` property.

Example:

```properties
API_KEY=YOUR_API_KEY
```

### Understanding the Code Structure:
- <b>commonMain</b>: Contains platform-agnostic code, including data models, network logic, and core business logic.
- <b>androidMain</b>: Specific code for the Android platform, such as UI components using Compose.
- <b>desktopMain</b>: Specific code for desktop platforms (Windows and macOS), likely using Compose for desktop as well.
- <b>iosMain</b>: Specific code for the iOS platform, potentially using Compose for iOS (still under development).

## License

```
MIT License

Copyright (c) 2024 Aritra Das

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
