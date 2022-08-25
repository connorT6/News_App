package com.connort6.newsapp.other

class Constants {
    companion object {
        const val API_KEY = "63b02b0976e043d59c719ea7f52dcb3e"
        val countryMap: HashMap<String, String> = HashMap()
        val categoryList: ArrayList<String> = ArrayList()
        val languageMap: HashMap<String, String> = HashMap()

        fun initializeData() {
            addDetailsToMap()
            addCategoriesToList()
            addToLanguageMap()
        }

        private fun addDetailsToMap() {
            countryMap["United Arab Emirates"] = "ae"
            countryMap["Argentina"] = "ar"
            countryMap["Austria"] = "at"
            countryMap["Australia"] = "au"
            countryMap["Belgium"] = "be"
            countryMap["Bulgaria"] = "bg"
            countryMap["Brazil"] = "br"
            countryMap["Canada"] = "ca"
            countryMap["Switzerland"] = "ch"
            countryMap["China"] = "cn"
            countryMap["Colombia"] = "co"
            countryMap["Cuba"] = "cu"
            countryMap["Czech Republic"] = "cz"
            countryMap["Germany"] = "de"
            countryMap["Egypt"] = "eg"
            countryMap["France"] = "fr"
            countryMap["United Kingdom"] = "gb"
            countryMap["Greece"] = "gr"
            countryMap["Hong Kong"] = "hk"

            countryMap["United States"] = "us"
        }

        private fun addCategoriesToList() {
            categoryList.add("business")
            categoryList.add("entertainment")
            categoryList.add("general")
            categoryList.add("health")
            categoryList.add("science")
            categoryList.add("sports")
            categoryList.add("technology")
        }

        private fun addToLanguageMap() {
            languageMap["Arabic"] = "ar"
            languageMap["German"] = "de"
            languageMap["English"] = "en"
            languageMap["Spanish"] = "es"
            languageMap["French"] = "fr"
            languageMap["Hebrew"] = "he"
            languageMap["Italian"] = "it"
            languageMap["Dutch"] = "nl"
            languageMap["Norwegian"] = "no"
            languageMap["Portuguese"] = "pt"
            languageMap["Russian"] = "ru"
            languageMap["Swedish"] = "sv"
            languageMap["Chinese"] = "zh"
        }
    }


}
