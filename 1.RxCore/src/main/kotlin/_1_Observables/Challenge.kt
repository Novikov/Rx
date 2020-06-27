package _1_Observables

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

fun main(){
    exampleOf("never") {
        val disposables = CompositeDisposable()
        val observable = Observable.never<Any>()
        //Полезные методы, которые можно вызывать в момент завершения, подписки и очистки
        val subscription = observable
                .doOnNext { println(it) }
                .doOnComplete { println("Completed") }
                .doOnSubscribe { println("Subscribed") }
                .doOnDispose { println("Disposed") }
                .subscribeBy(onNext = {
                    println(it)
                }, onComplete = {
                    println("Completed")
                })
        disposables.add(subscription)
        disposables.dispose()
    }
}