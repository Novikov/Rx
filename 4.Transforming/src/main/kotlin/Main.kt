import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

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


    //На каждый элемент Observable выпускает 0, 1 или несколько элементов другого Observable, а затем сглаживает всё в один Observable
    //Происходит подмена одного Observable на другой
    exampleOf("flatMap") {
        val observable = Observable
                .just("A", "B", "C")
                .flatMap { s: String ->
                    Observable.just(s + "1", s + "2", s + "3")
                }

        observable.subscribe { s: String -> print("$s ") }
    }

    //Тоже самое, что и flatMap, только гарантируется порядок элементов
    exampleOf("concatMap") {
        val observable = Observable
                .just("A", "B", "C")
                .concatMap { s: String ->
                    Observable.just(s + "1", s + "2", s + "3")
                }

        observable.subscribe { s: String -> print("$s ") }
    }

    exampleOf("switchMap") {
        val observable = Observable
                .just("A", "B", "C","D","E")
                .switchMap { s: String ->
                    Observable.just(s + "1", s + "2", s + "3").delay(1, TimeUnit.MICROSECONDS)
                }

        observable.subscribe { s: String -> print("$s ") }
    }

    exampleOf("debounce") {
        val observable = Observable
            .just("A", "B", "C","D","E")
            .debounce(1,TimeUnit.SECONDS)

        observable.subscribe { s: String -> print("$s ") }
    }




}
