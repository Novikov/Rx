import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

fun main(args: Array<String>) {

    exampleOf("toList") {
        val subscriptions = CompositeDisposable()
        val items = Observable.just("A", "B", "C")
        subscriptions.add(
                items
                        .toList()
                        .subscribeBy {
                            println(it)
                        }
        )
    }

    exampleOf("map") {
        val subscriptions = CompositeDisposable()
        subscriptions.add(
                Observable.just("M", "C", "V", "I")
                        .map {
                            it.romanNumeralIntValue()
                        }
                        .subscribeBy {
                            println(it)
                        })
    }

    exampleOf("map2") {
        val subscriptions = CompositeDisposable()
        subscriptions.add(
                Observable.just(1,2,3,4,5)
                        .map {
                            it*10
                        }
                        .subscribeBy {
                            println(it)
                        })
    }

    exampleOf("flatMap") {
        val observable = Observable
                .just("A", "B", "C")
                .switchMap { s: String ->
                    println()
                    Observable.just(s + "1", s + "2", s + "3")
                }

        observable.subscribe { s: String -> print("$s ") }
    }


}
