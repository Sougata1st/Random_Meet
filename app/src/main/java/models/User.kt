package models

class User {
    private var uId: String? = null
    private var name: String? = null
    private var profile: String? = null
    private var city: String? = null
    private var coins: Long = 0

    constructor() {}
    constructor(uId: String?, name: String?, profile: String?, city: String?, coins: Long) {
        this.uId = uId
        this.name = name
        this.profile = profile
        this.city = city
        this.coins = coins
    }

    fun getuId(): String? {
        return uId
    }

    fun setuId(uId: String?) {
        this.uId = uId
    }
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getProfile(): String? {
        return profile
    }

    fun setProfile(profile: String?) {
        this.profile = profile
    }

    fun getCity(): String? {
        return city
    }

    fun setCity(city: String?) {
        this.city = city
    }

    fun getCoins(): Long {
        return coins
    }

    fun setCoins(coins: Long) {
        this.coins = coins
    }
}