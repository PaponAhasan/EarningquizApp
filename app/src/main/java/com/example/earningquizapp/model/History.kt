package com.example.earningquizapp.model

class History{
    var coin: String = ""
    var timeAndDate: String = ""
    var isWithdrawal: Boolean = false

    constructor()

    constructor(coin: String, timeAndDate: String, isWithdrawal: Boolean) {
        this.coin = coin
        this.timeAndDate = timeAndDate
        this.isWithdrawal = isWithdrawal
    }
}
