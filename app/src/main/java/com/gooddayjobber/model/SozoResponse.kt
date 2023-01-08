package com.gooddayjobber.model

import com.google.gson.annotations.SerializedName

data class SozoResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("affiliate_link")
    var affiliateLink: String? = null,
    @SerializedName("frequency")
    var frequency: String? = null,
    @SerializedName("first_loan")
    var firstLoan: String? = null,
    @SerializedName("max_amount")
    var maxAmount: String? = null,
    @SerializedName("min_years")
    var minYears: String? = null,
    @SerializedName("max_years")
    var maxYears: String? = null,
    @SerializedName("receiving_time")
    var receivingTime: String? = null,
    @SerializedName("zero_percent")
    var zeroPercent: Int? = null,
    @SerializedName("slogan")
    var slogan: String? = null
)
