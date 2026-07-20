package dev.shaper.akdymall.features.data.user.identity

interface BaseIdentity {
    val zipCode: String?
    val roadAddress: String?
    val detailAddress: String?
    val name: String
    val age: Short
    val gender : UserGender
}