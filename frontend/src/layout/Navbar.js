import React from 'react'
import { Link} from 'react-router-dom'

export default function Navbar() {
  return (
    <div>
        <nav className="navbar navbar-dark bg-dark">
            <div className="container-fluid">
                <a class="navbar-brand mb-0 h1">
                    kaloriinnhold
                    </a>
                <div className="btn-group" role="group" aria-label="Basic example">
                      <Link type="button" className="btn btn-outline-light" to="/">Dishes</Link>
                      <Link type="button" className="btn btn-outline-light" to="/ingredients">Ingredients</Link>
                </div>
                <div className="btn-group" role="group" aria-label="Basic example">
                    <Link className="btn btn-outline-light" to="/addDish">
                        Create dish
                    </Link>
                    <Link className="btn btn-outline-light" to="/addIngredient">
                        Create ingredient
                    </Link>
                </div>

            </div>
        </nav>
    </div>
  )
}
