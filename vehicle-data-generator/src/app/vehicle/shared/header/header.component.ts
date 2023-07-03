import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/authentication/auth-service/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  onLogout(): void {
    this.tokenStorage.logout();
    this.router.navigate(['auth', 'login']);
  }
}
