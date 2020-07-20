import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject

fun main(args: Array<String>) {
    exampleOf("startWith") {
        val subscriptions = CompositeDisposable()
        val missingNumbers = Observable.just(3, 4, 5)
        val completeSet = missingNumbers.startWith(listOf(1, 2))

        completeSet
                .subscribe { number ->
                    println(number)
                }
                .addTo(subscriptions)
    }

    exampleOf("concat") {
        val subscriptions = CompositeDisposable()
        val first = Observable.just(1, 2, 3)
        val second = Observable.just(4, 5, 6)
        Observable.concat(first, second)
                .subscribe { number ->
                    println(number)
                }
                .addTo(subscriptions)
    }

    exampleOf("concatWith") {
        val subscriptions = CompositeDisposable()
        val germanCities = Observable.just("Berlin", "Münich", "Frankfurt")
        val spanishCities = Observable.just("Madrid", "Barcelona", "Valencia")

        germanCities
                .concatWith(spanishCities)
                .subscribe { number ->
                    println(number)
                }
                .addTo(subscriptions)
    }


    exampleOf("concatMap") {
        val subscriptions = CompositeDisposable()
        val countries = Observable.just("Germany", "Spain")
        val observable = countries
                .concatMap {
                    when (it) {
                        "Germany" -> Observable.just("Berlin", "Münich", "Frankfurt")
                        "Spain" -> Observable.just("Madrid", "Barcelona", "Valencia")
                        else -> Observable.empty<String>()
                    }
                }
        observable
                .subscribe { city ->
                    println(city)
                }
                .addTo(subscriptions)
    }

    exampleOf("merge") {
        val subscriptions = CompositeDisposable()
        val left = PublishSubject.create<Int>()
        val right = PublishSubject.create<Int>()

        Observable.merge(left, right)
                .subscribe {
                    println(it)
                }
                .addTo(subscriptions)

        left.onNext(0)
        left.onNext(1)
        right.onNext(3)
        left.onNext(4)
        right.onNext(5)
        right.onNext(6)
    }

    exampleOf("combineLatest") {
        val subscriptions = CompositeDisposable()
        val left = PublishSubject.create<String>()
        val right = PublishSubject.create<String>()

        Observables.combineLatest(left, right) { leftString, rightString ->
            "$leftString $rightString"
        }.subscribe {
            println(it)
        }.addTo(subscriptions)

        left.onNext("Hello")
        right.onNext("World")
        left.onNext("It’s nice to")
        right.onNext("be here!")
        left.onNext("Actually, it’s super great to")
    }

    exampleOf("zip") {
        val subscriptions = CompositeDisposable()
        val left = PublishSubject.create<String>()
        val right = PublishSubject.create<String>()

        Observables.zip(left, right) { weather, city ->
            "It’s $weather in $city"
        }.subscribe {
            println(it)
        }.addTo(subscriptions)

        left.onNext("sunny")
        right.onNext("Lisbon")
        left.onNext("cloudy")
        right.onNext("Copenhagen")
        left.onNext("cloudy")
        right.onNext("London")
        left.onNext("sunny")
        right.onNext("Madrid")
        right.onNext("Vienna")
    }

    exampleOf("withLatestFrom") {
        val subscriptions = CompositeDisposable()

        val button = PublishSubject.create<Unit>()
        val editText = PublishSubject.create<String>()

        button.withLatestFrom(editText) { _: Unit, value: String ->
            value
        }.subscribe {
            println(it)
        }.addTo(subscriptions)

        editText.onNext("Par")
        editText.onNext("Pari")
        editText.onNext("Paris")
        button.onNext(Unit)
        button.onNext(Unit)
    }

    exampleOf("amb") {
        val subscriptions = CompositeDisposable()
        val left = PublishSubject.create<String>()
        val right = PublishSubject.create<String>()

        left.ambWith(right)
                .subscribe {
                    println(it)
                }
                .addTo(subscriptions)

        left.onNext("Lisbon")
        right.onNext("Copenhagen")
        left.onNext("London")
        left.onNext("Madrid")
        right.onNext("Vienna")
    }

    exampleOf("scan") {
        val subscriptions = CompositeDisposable()
        val source = Observable.just(1, 3, 5, 7, 9)

        source
                .scan(0) { a, b -> a + b }
                .subscribe {
                    println(it)
                }
                .addTo(subscriptions)
    }


}