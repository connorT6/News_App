package com.connort6.newsapp.other

class Constants {
    companion object {
        const val API_KEY = "1957e2e6764c4db88ac5c9dd5f2e1a4e"
        val countryMap: HashMap<String, String> = HashMap()
        val categoryList: ArrayList<String> = ArrayList()
        fun initializeData() {
            addDetailsToMap()
            addCategoriesToList()
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
    }


}
