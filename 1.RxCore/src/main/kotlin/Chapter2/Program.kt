package Chapter2

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.io.File
import java.io.FileNotFoundException

fun main(){
    exampleOf("just") {
        val observable: Observable<Int> = Observable.just(1)
    }

    exampleOf("fromIterable") {
        val observable: Observable<Int> =
                Observable.fromIterable(listOf(1, 2, 3))
    }

    exampleOf("subscribe") {
        val observable = Observable.just(1, 2, 3)
        //Тут можно определить только событие onNext
        observable.subscribe { println(it) }
    }

    exampleOf("empty") {
        val observable = Observable.empty<Unit>()
        //Тут можно пределить 3 типа событий в любом порядке. Это extensionFunction из RxKotlin
        observable.subscribeBy(onComplete = { println("Completed") }, onNext = { println(it) })
    }

    exampleOf("never") {
        val observable = Observable.never<Any>()
        observable.subscribeBy(
                onNext = { println(it) },
                onComplete = { println("Completed") }
        )
    }

    exampleOf("dispose") {
        val mostPopular: Observable<String> = Observable.just("A", "B", "C")
        val subscription = mostPopular.subscribe {
            println(it)
        }
        //отписка
        subscription.dispose()
    }

    exampleOf("CompositeDisposable") {
        val subscriptions = CompositeDisposable()
        val disposable = Observable.just("A", "B", "C")
                .subscribe {
                    println(it)
                }
        subscriptions.add(disposable)
//        Вызов dispose для всех disposable
        subscriptions.dispose()
    }

    exampleOf("create") {
        val disposables = CompositeDisposable()
        Observable.create<String> { emitter ->
            emitter.onNext("1")
            //выпуск ошибки
            emitter.onError(RuntimeException("Error"))
            emitter.onComplete()
            emitter.onNext("?")
        }.subscribeBy(
                onNext = { println(it) },
                onComplete = { println("Completed") },
                onError = { println(it) }
        )
    }

    //Фабрика observable
    exampleOf("defer") {
        val disposables = CompositeDisposable()
        var flip = false
        val factory: Observable<Int> = Observable.defer {
            flip = !flip
            if (flip) {
                Observable.just(1, 2, 3)
            } else {
                Observable.just(4, 5, 6)
            }
        }

        //подписка на фабрику несколько раз
        for (i in 0..3) {
            disposables.add(
                    factory.subscribe {
                        println(it)
                    }
            )
        }
        disposables.dispose()
    }

    //Single observable
    exampleOf("Single") {
        val subscriptions = CompositeDisposable()
        fun loadText(filename: String): Single<String> {
            return Single.create create@{ emitter ->
                val file = File(filename)
                if (!file.exists()) {
                    emitter.onError(FileNotFoundException("Can’t find $filename"))
                    return@create
                }
                val contents = file.readText(Charsets.UTF_8)
                emitter.onSuccess(contents)
            }
        }

        val observer = loadText("copyright.txt")
                .subscribeBy(
                        onSuccess = { println(it) },
                        onError = { println("Error, $it") })
        subscriptions.add(observer)
    }


}